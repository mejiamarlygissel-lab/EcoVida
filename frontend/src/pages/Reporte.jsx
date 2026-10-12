import { useEffect, useState } from "react";
import { generarReportePorIdUsuario } from "../services/ReporteService";
import { listarUsuarios } from "../services/UsuarioService";
import "./Reporte.css";

function Reporte() {

  const [usuarios, setUsuarios] = useState([]);
  const [reporte, setReporte] = useState([]);

  const [usuario, setUsuario] = useState("");
  const [residuo, setResiduo] = useState("Todos");

  const [desde, setDesde] = useState("");
  const [hasta, setHasta] = useState("");

  const [mostrarReporte, setMostrarReporte] = useState(false);

  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");

  const [fechaMinima, setFechaMinima] = useState("");
  const [fechaMaxima, setFechaMaxima] = useState("");


  // =====================================================
  // CARGAR USUARIOS
  // =====================================================

  useEffect(() => {
    cargarUsuarios();
  }, []);


  const cargarUsuarios = async () => {

    try {

      const respuesta = await listarUsuarios();


      setUsuarios(respuesta);

    } catch (error) {

      console.error(
        "Error al cargar usuarios:",
        error
      );

      setError(
        "No fue posible cargar los usuarios."
      );
    }
  };


  // =====================================================
  // CAMBIO DE USUARIO
  // =====================================================

  const handleUsuarioChange = async (e) => {

    const idUsuario = e.target.value;

    setUsuario(idUsuario);

    // Limpiar datos anteriores
    setReporte([]);
    setMostrarReporte(false);
    setError("");

    if (!idUsuario) {

      setDesde("");
      setHasta("");

      setFechaMinima("");
      setFechaMaxima("");

      return;
    }

    try {

      console.log(
        "CONSULTANDO REPORTES DEL USUARIO:",
        idUsuario
      );

      const respuesta =
        await generarReportePorIdUsuario(idUsuario);

      const reportes = respuesta.data;

      console.log(
        "REPORTES DEL USUARIO:",
        reportes
      );


      // =================================================
      // SI NO TIENE REPORTES
      // =================================================

      if (
        !reportes ||
        reportes.length === 0
      ) {

        setDesde("");
        setHasta("");

        setFechaMinima("");
        setFechaMaxima("");

        setError(
          "Este usuario no tiene reportes registrados."
        );

        return;
      }


      // =================================================
      // OBTENER FECHAS
      // =================================================

      const fechas = reportes
        .map((item) => item.fecha)
        .filter((fecha) => fecha);


      if (fechas.length > 0) {

        const fechasOrdenadas =
          [...fechas].sort();

        const fechaMasAntigua =
          fechasOrdenadas[0];

        const fechaMasReciente =
          fechasOrdenadas[
          fechasOrdenadas.length - 1
          ];


        setFechaMinima(
          fechaMasAntigua
        );

        setFechaMaxima(
          fechaMasReciente
        );

        // Colocamos automáticamente
        // el rango completo disponible

        setDesde(
          fechaMasAntigua
        );

        setHasta(
          fechaMasReciente
        );
      }


      setError("");

    } catch (error) {

      console.error(
        "Error al cargar reportes del usuario:",
        error
      );

      setError(
        "No fue posible obtener los datos del usuario."
      );
    }
  };


  // =====================================================
  // CONSULTAR REPORTE
  // =====================================================

  const handleConsultar = async (e) => {
    

    e.preventDefault();

    if (!usuario) {

      setError(
        "Debe seleccionar un usuario."
      );

      return;
    }


    setCargando(true);
    setError("");


    try {

      console.log(
        "ID USUARIO:",
        usuario
      );


      // =================================================
      // OBTENER REPORTES DEL USUARIO
      // =================================================

      const respuesta =
        await generarReportePorIdUsuario(
          usuario
        );

      const reportesUsuario =
        respuesta.data;


      console.log(
        "REPORTES DEL USUARIO:",
        reportesUsuario
      );


      // =================================================
      // FILTRAR REPORTES
      // =================================================

      const reportesFiltrados =
        reportesUsuario.filter((item) => {


          // ---------------------------------------------
          // FILTRO POR TIPO DE RESIDUO
          // ---------------------------------------------

          const coincideResiduo =
            residuo === "Todos" ||
            item.tipoResiduo?.toLowerCase() ===
            residuo.toLowerCase();


          // ---------------------------------------------
          // FILTRO DESDE
          // ---------------------------------------------

          const coincideDesde =
            !desde ||
            item.fecha >= desde;


          // ---------------------------------------------
          // FILTRO HASTA
          // ---------------------------------------------

          const coincideHasta =
            !hasta ||
            item.fecha <= hasta;


          return (
            coincideResiduo &&
            coincideDesde &&
            coincideHasta
          );

        });


      console.log(
        "REPORTES FILTRADOS:",
        reportesFiltrados
      );


      // =================================================
      // SI NO HAY RESULTADOS
      // =================================================

      if (
        reportesFiltrados.length === 0
      ) {

        setError(
          "No se encontraron reportes con los filtros seleccionados."
        );

        setReporte([]);

        setMostrarReporte(false);

        return;
      }


      // =================================================
      // GUARDAR RESULTADOS
      // =================================================

      setReporte(
        reportesFiltrados
      );

      setMostrarReporte(
        true
      );


    } catch (error) {

      console.error(
        "Error al consultar el reporte:",
        error
      );

      setError(
        "No fue posible cargar el reporte."
      );

    } finally {

      setCargando(false);

    }
  };


  // =====================================================
  // VOLVER
  // =====================================================

  const handleVolverConsultar = () => {

    setMostrarReporte(false);

    setReporte([]);

    setError("");

  };


  // =====================================================
  // VISTA
  // =====================================================

  return (

    <div className="reporte-main-container">


      {/* =================================================
          FILTROS
      ================================================= */}

      {!mostrarReporte ? (

        <div className="reporte-filtros-card centrado">

          <div className="reporte-card-header-title">

            <h2>
              GESTIÓN DE REPORTES
            </h2>

            <p>
              ECO VIDA 
            </p>

          </div>


          <form
            onSubmit={handleConsultar}
            className="reporte-filtros-form"
          >


            {/* =========================================
                USUARIO
            ========================================= */}

            <div className="reporte-filtro-grupo">

              <label>
                Usuario:
              </label>

              <select
                value={usuario}
                onChange={handleUsuarioChange}
                className="reporte-filtro-select"
              >

                <option value="">
                  Seleccione un usuario
                </option>


                {usuarios?.map((item) => (

                  <option
                    key={item.idUsuario}
                    value={item.idUsuario}
                  >

                    {item.nombre} {item.apellido}

                  </option>

                ))}

              </select>

            </div>


            {/* =========================================
                TIPO DE RESIDUO
            ========================================= */}

            <div className="reporte-filtro-grupo">

              <label>
                Tipo de Residuo:
              </label>

              <select
                value={residuo}
                onChange={(e) => setResiduo(e.target.value)}
                className="reporte-filtro-select"
              >
                <option value="Todos">Todos</option>

                <option value="Papel">Papel</option>

                <option value="Cartón">Cartón</option>

                <option value="Plastico">Plástico</option>

                <option value="Vidrio">Vidrio</option>

                <option value="Metal">Metal</option>
              </select>
            </div>


            {/* =========================================
                FECHAS
            ========================================= */}

            <div className="reporte-filtro-fila-fechas">


              <div className="reporte-filtro-grupo">

                <label>
                  Desde:
                </label>

                <input
                  type="date"
                  value={desde}
                  min={fechaMinima}
                  max={fechaMaxima}
                  onChange={(e) =>
                    setDesde(e.target.value)
                  }
                  className="reporte-filtro-input"
                />

              </div>


              <div className="reporte-filtro-grupo">

                <label>
                  Hasta:
                </label>

                <input
                  type="date"
                  value={hasta}
                  min={fechaMinima}
                  max={fechaMaxima}
                  onChange={(e) =>
                    setHasta(e.target.value)
                  }
                  className="reporte-filtro-input"
                />

              </div>

            </div>


            {/* =========================================
                ERROR
            ========================================= */}

            {error && (

              <div className="reporte-error">
                {error}
              </div>

            )}


            {/* =========================================
                BOTÓN
            ========================================= */}

            <div className="reporte-filtro-acciones">

              <button
                type="submit"
                className="reporte-btn-consultar"
                disabled={cargando}
              >

                {cargando
                  ? "Consultando..."
                  : "🔍 Consultar"
                }

              </button>

            </div>


          </form>

        </div>


      ) : (


        /* =================================================
           REPORTE
        ================================================= */

        <div className="reporte-resultado-card animate-fade-in">


          <div className="reporte-header">

            <h2>
              REPORTE DE RESIDUOS
            </h2>

            <h3>
              ECO VIDA S.A.S
            </h3>

          </div>


          <div className="reporte-divider-line"></div>


          {/* =================================================
              MOSTRAR TODOS LOS PROCESOS FILTRADOS
          ================================================= */}

          {reporte.map((item, index) => (

            <div
              key={`${item.idRecoleccion}-${index}`}
              className="reporte-proceso"
            >


              <h3 className="reporte-proceso-titulo">

                PROCESO #{index + 1}

              </h3>


              {/* =========================================
                  USUARIO
              ========================================= */}

              <div className="reporte-section">

                <h4>
                  INFORMACIÓN DEL USUARIO
                </h4>

                <p>
                  <strong>
                    Nombre:
                  </strong>{" "}

                  {item.nombre}{" "}
                  {item.apellido}

                </p>

                <p>
                  <strong>
                    Correo:
                  </strong>{" "}

                  {item.correo}

                </p>

              </div>


              <div className="reporte-divider-line"></div>


              {/* =========================================
                  RECOLECCIÓN
              ========================================= */}

              <div className="reporte-section">

                <h4>
                  RECOLECCIÓN
                </h4>

                <p>
                  <strong>
                    ID:
                  </strong>{" "}

                  {item.idRecoleccion}

                </p>

                <p>
                  <strong>
                    Fecha:
                  </strong>{" "}

                  {item.fecha}

                </p>

                <p>
                  <strong>
                    Tipo de residuo:
                  </strong>{" "}

                  {item.tipoResiduo}

                </p>

                <p>
                  <strong>
                    Cantidad:
                  </strong>{" "}

                  {item.cantidadKg} kg

                </p>

                <p>
                  <strong>
                    Ubicación:
                  </strong>{" "}

                  {item.ubicacion}

                </p>

              </div>


              <div className="reporte-divider-line"></div>


              {/* =========================================
                  CLASIFICACIÓN
              ========================================= */}

              <div className="reporte-section">

                <h4>
                  CLASIFICACIÓN
                </h4>

                <p>
                  <strong>
                    Categoría:
                  </strong>{" "}

                  {item.categoria ||
                    "Sin clasificación"}

                </p>

                <p>
                  <strong>
                    Descripción:
                  </strong>{" "}

                  {item.descripcion ||
                    "Sin descripción"}

                </p>

                <p>
                  <strong>
                    Fecha:
                  </strong>{" "}

                  {item.fechaClasificacion ||
                    "Sin registro"}

                </p>

              </div>


              <div className="reporte-divider-line"></div>


              {/* =========================================
                  SEGUIMIENTO
              ========================================= */}

              <div className="reporte-section">

                <h4>
                  SEGUIMIENTO
                </h4>

                <p>
                  <strong>
                    Estado:
                  </strong>{" "}

                  {item.estado ||
                    "Sin seguimiento"}

                </p>

                <p>
                  <strong>
                    Tipo de proceso:
                  </strong>{" "}

                  {item.tipoProceso ||
                    "Sin registro"}

                </p>

                <p>
                  <strong>
                    Resultado:
                  </strong>{" "}

                  {item.resultado ||
                    "Sin resultado"}

                </p>

                <p>
                  <strong>
                    Fecha:
                  </strong>{" "}

                  {item.fechaSeguimiento ||
                    "Sin registro"}

                </p>

              </div>


              <div className="reporte-divider-line"></div>

            </div>

          ))}


          {/* =================================================
              BOTONES
          ================================================= */}

          <div className="reporte-acciones-botones">

            <button
              className="reporte-btn-pdf"
              onClick={() => window.print()}
            >

              📄 IMPRIMIR / PDF

            </button>


            <button
              className="reporte-btn-volver"
              onClick={handleVolverConsultar}
            >

              ← Nueva Consulta

            </button>

          </div>


        </div>

      )}

    </div>

  );

}

export default Reporte;
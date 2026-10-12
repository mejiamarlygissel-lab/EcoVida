import React from "react";
import "./Login.css";
import hojas from "../assets/Hojas.png";

import { login } from "../services/UsuarioService";
import { useState } from "react";
import { Link } from "react-router-dom";

import { useNavigate } from "react-router-dom";

function Login() {

  // Almacena el correo ingresado por el usuario
  const [correo, setCorreo] = useState("");

  // Almacena la contraseña ingresada por el usuario
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const [errores, setErrores] = useState({})


  // Función que se ejecuta al presionar el botón "Ingresar"
  async function iniciarSesion(e) {
    e.preventDefault();

    const erroresLogin = {}

    if (!correo.trim()) {
      erroresLogin.correo = "Ingrese su correo electronico";

    }


    if (!password.trim()) {
      erroresLogin.password = "Ingrese su contraseña";

    }

    setErrores(erroresLogin);

    if (Object.keys(erroresLogin).length > 0) {

      return;

    }



    // Elimina cualquier sesión anterior
    localStorage.removeItem("token");
    localStorage.removeItem("correo");


    try {

      const respuesta = await login({
        correo,
        password,
      });

      localStorage.setItem("token", respuesta.data.token);
      localStorage.setItem("correo", respuesta.data.correo);
      localStorage.setItem("rol", respuesta.data.rol);



      alert("¡Bienvenido a Eco Vida!");

      navigate("/Dashboard");

    } catch (error) {

      console.log(error);

      alert("Correo o contraseña incorrectos.");
    }


  }
  return (

    <div className="Login-container">

      {/* Tarjeta de presentación del módulo */}
      <div className="card-modulo">

        <h1>¡Bienvenido(a) a Eco Vida!</h1>

        <p>
          ¡Ingrese ya ! ¿que estás esperando ?
        </p>

        <div className="login-container">

          {/* Sección izquierda donde se muestra la imagen */}
          <div className="login-left">

            <img src={hojas} alt="Naturaleza" />

            <div className="overlay">

            </div>

          </div>

          {/* Sección derecha que contiene el formulario */}
          <div className="login-right">

            <div className="login-box">

              <h2>Iniciar sesión</h2>

              {/* Campo para ingresar el correo */}
              <label>CorreoElectronico</label>

              <input
                type="text"
                placeholder="Ingrese su correo"
                value={correo}
                onChange={(e) => setCorreo(e.target.value)}
              />
              {errores.correo && (
                <div className="login-mensaje-error">
                  ❌ {errores.correo}
                </div>
              )}


              {/* Campo para ingresar la contraseña */}
              <label>Contraseña</label>

              <input
                type="password"
                placeholder="Ingrese su contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}

                
              />

              {errores.password && (
                <div className="login-mensaje-error">
                  ❌ {errores.password}
                </div>
              )}



              {/* Botón que ejecuta el proceso de autenticación */}
              <button onClick={iniciarSesion}>
                Ingresar
              </button>

              {/* Enlace para dirigirse al registro de usuarios */}
              <p className="registro">
                ¿No tienes cuenta?

                <Link to="/Formulario">Regístrate</Link>
              </p>

            </div>

          </div>

        </div>

      </div>


    </div>


  );
}

export default Login;
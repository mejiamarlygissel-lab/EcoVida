import React from "react";
import './Bienvenida.css';


function Bienvenida() {

  function Bienvenida({ setModuloActivo }) {
    
  return (
    <div className="bienvenida-container">


      <div className="left-section">
        <div className="header">
          <div className="logo"></div>
          <div className="studio-name">¡ME ENCANTAS QUE ESTES AQUI!</div>
        </div>

        <div className="text-content">
          <p className="subtitle">¡BIENVENIDOS A!</p>
          <h1 className="main-title">ECO VIDA S.A.S</h1>

          <button
            type="button"
            onClick={() => {
              console.log("Botón presionado");
              setModuloActivo("index");
            }}
          >
            Ingresar
          </button>



        </div>

        <div className="background-waves"></div>
      </div>


      <div className="right-section"></div>

    </div>
  );
}
}

export default Bienvenida;
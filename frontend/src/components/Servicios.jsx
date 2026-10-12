import React from "react";
import { useState } from "react";
import recoleccion from "../assets/recoleccion.jpg";
import clasificacion7 from "../assets/clasificacion7.jpg";
import image from "../assets/image.png";
import './Servicios.css';

function Servicios() {


    return (

        <div className="container">
            <h2 className="center-align"><strong>Nuestros Servicios</strong></h2>

            <div className="row">


                <div className="col s12 m4">
                    <div className="card">
                        <div className="card-image">
                            <img src={recoleccion} alt="recolección" />


                        </div>


                        <div className="card-content">

                            <h3>Recolección</h3>


                            <p>Gestionamos el registro y control de la información relacionada con la recolección de residuos reciclables, permitiendo llevar un seguimiento organizado de cada proceso y facilitando una administración eficiente de los datos.</p>
                        </div>
                    </div>
                </div>




                <div className="col s12 m4">
                    <div className="card">
                        <div className="card-image">
                            <img src={clasificacion7} alt="Clasificación7" />
                        </div>
                        <div className="card-content">

                            <h3>Clasificación</h3>

                            <p>Administramos la información correspondiente a la clasificación de los residuos reciclables, organizando los registros según su categoría para mantener un control claro, ordenado y accesible dentro del sistema.</p>
                        </div>
                    </div>
                </div>



                <div className="col s12 m4">
                    <div className="card">
                        <div className="card-image">
                            <img src={image} alt="image" />
                        </div>
                        <div className="card-content">

                            <h3> Seguimiento de residuo</h3>


                            <p>Gestionamos el seguimiento de los residuos reciclables mediante el registro de su estado y avance dentro del proceso. Esto permite consultar su historial, mantener la trazabilidad de la información y apoyar una gestión ambiental organizada.</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    )
}

export default Servicios;
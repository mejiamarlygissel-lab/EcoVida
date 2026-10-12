import React from "react";
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import './Index.css';
import Servicios from "../components/Servicios";
import logoimagen from "../assets/logoimagen.jpg";
import { Router } from "react-router-dom";
import { Link } from "react-router-dom";


function Index() {


    return (


        <>

            <Navbar />

            {/* HERO */}
            <section className="hero">

                <div className="hero-overlay">



                    <h1> EcoVida</h1>


                    <h2>Sistema de Gestión de Residuos Reciclables</h2>

                    <p>
                        Organiza, registra y consulta la información relacionada con la
                        recolección, clasificación y seguimiento de residuos reciclables
                        de una manera sencilla, segura y organizada.
                    </p>



                    <Link to="/Login " className="hero-btn">

                        Iniciar sesión

                    </Link>


                    <Link to="/Usuarios " className="hero-btn">

                        Registrarse

                    </Link>

                </div>

            </section>


            {/* QUIÉNES SOMOS */}
            <main className="index-main-content">

                <div className="container">

                    <div className="card">

                        <div className="card-content">

                            <img
                                src={logoimagen}
                                alt="EcoVida"
                                className="card-img"
                            />

                            <h2 className="index-card-title">
                                ¿Quiénes somos?
                            </h2>

                            <p>
                                EcoVida es un proyecto de software orientado a la gestión
                                de información relacionada con los residuos reciclables.
                                Su propósito es facilitar el registro, organización y
                                seguimiento de los procesos asociados a la recolección,
                                clasificación y seguimiento de residuos, promoviendo una
                                administración clara, ordenada y accesible.
                            </p>

                            <section>

                                <h2 className="index-card-title">
                                    ¿A qué nos dedicamos?
                                </h2>

                                <p>
                                    EcoVida es un sistema de gestión de información diseñado
                                    para registrar, organizar y consultar los datos
                                    relacionados con la recolección, clasificación y
                                    seguimiento de residuos reciclables. Su objetivo es
                                    facilitar el control de la información, mantener un
                                    historial organizado de los procesos y apoyar una gestión
                                    ambiental más eficiente mediante el uso de herramientas
                                    tecnológicas.
                                </p>

                            </section>

                            <button className="btn-info">
                                Conoce nuestros servicios
                            </button>

                        </div>

                    </div>

                </div>

            </main>


            {/* SERVICIOS */}
            <Servicios />

            <Footer />

        </>
    )





}

export default Index;
import "./Footer.css";
import {
    FaPhone,
    FaEnvelope,
    FaRecycle,
} from "react-icons/fa";

function Footer() {

    return (

        <footer className="page-footer">

            <div className="container">

                <div className="row">

                    {/* Columna de información */}
                    <div className="col">

                        <h5><FaRecycle/> EcoVida</h5>

                        <p>
                            Sistema de Gestión de Residuos Reciclables.
                        </p>

                        <p>
                            <FaPhone/> 3440853576 - 3133667547
                        </p>

                        <p>
                            <FaEnvelope/> EcoVIDA25@gmail.com
                        </p>

                    </div>

                    

                </div>

                {/* Línea inferior */}
                <div className="footer-copy">

                      © 2026 EcoVida S.A.S. - Sistema de Gestión de Residuos Reciclables.

                </div>

            </div>

        </footer>

    );

}

export default Footer;
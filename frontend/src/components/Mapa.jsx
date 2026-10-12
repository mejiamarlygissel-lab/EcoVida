import { useState } from "react";
import {
    MapContainer,
    TileLayer,
    Marker,
    useMapEvents
} from "react-leaflet";

import { obtenerUbicacion } from "../services/UbicacionService";


// Detecta el clic en el mapa
function SeleccionarUbicacion({ setPosicion }) {

    useMapEvents({

        click(e) {
            setPosicion(e.latlng);
        }

    });

    return null;
}



function MapaUbicacion({ setUbicacion, setPosicion: guardarPosicion }) {

    const [posicion, setPosicion] = useState(null);


    const obtenerDireccion = async (lat, lng) => {

        try {

            const respuesta = await obtenerUbicacion(lat, lng);

            const datos = respuesta.data;


            if (datos.address) {

                const lugar =
                    datos.address.amenity ||
                    datos.address.shop ||
                    "";


                const direccion =
                    `${lugar ? lugar + " - " : ""}` +
                    `${datos.address.road || ""}` +
                    `${datos.address.house_number ? " #" + datos.address.house_number : ""}, ` +
                    `${datos.address.city || datos.address.town || ""}, ` +
                    `${datos.address.state || ""}`;


                setUbicacion(direccion);

            }


        } catch (error) {

            console.error(
                "Error obteniendo dirección:",
                error
            );

        }
    }





    return (

        <MapContainer
            center={[5.33775, -72.39586]}
            zoom={13}
            style={{ height: "400px", width: "100%" }}
        >

            <TileLayer
                attribution='&copy; OpenStreetMap contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />


            <SeleccionarUbicacion
                setPosicion={(pos) => {

                    setPosicion(pos);

                    guardarPosicion(pos);

                    obtenerDireccion(
                        pos.lat,
                        pos.lng
                    );

                }}
            />

            {
                posicion && (

                    <Marker position={posicion} />

                )
            }


        </MapContainer>

    );

}

export default MapaUbicacion;
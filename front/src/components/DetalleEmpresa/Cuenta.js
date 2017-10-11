import React from 'react';

const Cuenta = (props) => {
    return (
        <div className="cuenta">
            <div className="nombre">
                {props.cuentaX.cuenta.descripcion + ': $' + props.cuentaX.valor}
            </div>
        </div>
    )
}
export default Cuenta;
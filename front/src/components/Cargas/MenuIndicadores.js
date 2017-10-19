import React from 'react';
import {Link} from 'react-router';
import MenuComponent from '../commons/MenuComponent';

const MenuIndicadores = (props) =>
  <MenuComponent title="Que accion desea realizar?">
    <Link to="/cargar/newIndicador">Agregar Indicador</Link>
  </MenuComponent>;

export default MenuIndicadores;

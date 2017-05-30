import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import {periodosOrdenados} from '../../selectors/DataSelector';
import {Link} from 'react-router';
import MenuComponent from '../../components/commons/MenuComponent';

const Menu = (props) =>
  <MenuComponent title="Menu">
            <Link to="/cargar/cuenta"> Cargar cuenta </Link>
            <Link to="/menu/indicadores"> Cargar y/o eliminar indicadores </Link>
            <Link to="/cargar/metodologias"> Crear metodologias </Link>
            <Link to="/empresas"> Consultar por empresa y periodo </Link>
            <Link to="/analizar"> Realizar analisis </Link>
  </MenuComponent>;


export default Menu;

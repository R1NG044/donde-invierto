import React from 'react';
import { Route, IndexRoute } from 'react-router';
import App from './containers/App/App';
import Menu from './containers/Menu/Menu';
import HomePage from './containers/HomePage/HomePage';
import Metodologias from './containers/Metodologias/Metodologias';
import CargaEmpresa from './components/Cargas/CargaEmpresa';
import MenuIndicadores from './components/Cargas/MenuIndicadores';
import Carga from './containers/Cargas/Carga.js';
import NotFoundPage from './components/NotFoundPage/NotFoundPage';

export default (
  <Route path="/" component={App}>
    <IndexRoute component={Menu}/>
    <Route path="/empresas" component={HomePage}/>
    <Route path="/metodologias" component={Metodologias}/>
    <Route path="/cargar/:type" component={Carga}/>
    <Route path="/menu/indicadores" component={MenuIndicadores}/>
    <Route path="/empresasx" component={CargaEmpresa}/>
    <Route path="*" component={NotFoundPage}/>
  </Route>
);

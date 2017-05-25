import { createSelector } from 'reselect'

const empresasSelector = state => state.data.empresas
const add = (a,b) => a+b.cuentas.length;

export const periodosOrdenados = createSelector(
  empresasSelector,
  empresas => {

    let processedEmpresas = [];
    empresas.forEach(empresa => {
      let periodosAsObject = {};
      let empresaCopy = Object.assign({}, empresa);

      empresa.periodos.reduce((obj = periodosAsObject, periodo) => {
        obj[periodo.anio] ? obj[periodo.anio].push(periodo) : (obj[periodo.anio] = [periodo]);
      }, periodosAsObject);


      empresaCopy.totalPeriodos = empresa.periodos.length;
      empresaCopy.totalCuentas = empresa.periodos.reduce(add, 0);
      empresaCopy.periodos = periodosAsObject;

      processedEmpresas.push(empresaCopy);
    });

    return processedEmpresas;
  }
)

import React from 'react';
import './Login.scss';

export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.onInputChange = this.onInputChange.bind(this);
    }

    onInputChange(e) {
        this.props.uiActions.inputChanged('login')(e);
    }

    render() {
        return (
            <div className="Login">
                <form onSubmit={this.props.login}>
                    <p className="description"> Login </p>
                    <input type="text" name="username" placeholder="Nombre de usuario" onChange={this.onInputChange} value={this.props.inputsVals.username}/>
                    <input type="password" name="password" placeholder="Contraseña" onChange={this.onInputChange} value={this.props.inputsVals.password}/>
                    <input type="submit" />
                </form>
            </div>
        )
    }
}

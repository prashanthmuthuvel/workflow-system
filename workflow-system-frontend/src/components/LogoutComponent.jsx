import React, { Component } from 'react'
import { removeUserSession } from '../utils/common';

class LogoutComponent extends Component {
    constructor(props) {
        super(props)
    }

    componentDidMount(){
        removeUserSession();
        this.props.history.push('/');
    }

    render() {
        return(null)
    }

}

export default LogoutComponent
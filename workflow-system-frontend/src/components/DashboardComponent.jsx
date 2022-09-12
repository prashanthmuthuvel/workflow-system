import React, { Component } from 'react'
import StudentService from '../services/StudentService'
import ReactDOM from 'react-dom';
import Navbar from '../components/navbar/Navbar';
import HeaderComponent from '../components/HeaderComponent';
import { getUser, getIsAdmin, removeUserSession, setUserSession } from '../utils/common';

class DashboardComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            userName: ''
        }
    }

    componentDidMount(){
    }

    render() {
        const username = getUser()
        return (
            
            <div>
                <Navbar />
                <div className="container">
                <br></br>
                    <div className = "card col-md-6 offset-md-3">
                        <h3 className = "text-center">Welcome {username}</h3>
                    </div>
                </div>
            </div>
        )
    }
}

export default DashboardComponent

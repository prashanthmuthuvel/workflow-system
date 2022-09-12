import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';

class LogoutComponent extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div>
                <Navbar />
                <div className="container">
                <br></br>
                    <div className = "card col-md-6 offset-md-3">
                        <h3 className = "text-center"> Contact Us at</h3>
                        <div className = "card-body">
                            <div className = "row">
                                <label> Email: </label>
                                <div>pmuthuve@lakeheadu.ca</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default LogoutComponent
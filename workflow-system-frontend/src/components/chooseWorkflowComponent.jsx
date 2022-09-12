import React, { Component } from 'react'
import WorkflowService from '../services/WorkflowService'
import ReactDOM from 'react-dom';
import Navbar from '../components/navbar/Navbar';
import HeaderComponent from '../components/HeaderComponent';
import { getToken,setWorkflowName } from '../utils/common';

class ChooseWorkflowComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            userName: '',
            workflows: '',
            workflowPath: ''
        }
        this.handleChange = this.handleChange.bind(this);
        this.next = this.next.bind(this);
    }

    handleChange(event) {
        setWorkflowName(event.target.value)
    }

    next = (e) => {
        e.preventDefault();
        this.props.history.push('/createTicket');
    }

    componentDidMount(){
        const token = getToken();
        WorkflowService.getWorkflow(token).then( res => {
            this.setState({workflows: res.data});
        })
    }

    render() {
        return (
            <div>
                <Navbar />
                <div>
                    <br></br>
                    <div className = "container">
                            <div className = "row">
                                <div className = "card col-md-6 offset-md-3 offset-md-3">
                                    <h3 className="text-center">Workflow</h3>
                                    <div className = "card-body">
                                        <form>
                                            <div className = "form-group">
                                                <label> Choose Workflow </label>
                                                <select className="form-control" aria-label="Default select example"
                                                    onChange={this.handleChange}>
                                                    <option >Open this select menu</option>
                                                    {this.state.workflows.length > 0 && this.state.workflows.map((option, i) =>
                                                        <option value={option.optionLabel} key={i}>{option.optionLabel}</option>
                                    
                                                    )}
                                                </select>
                                            </div>
                                            
                                            <button className="btn btn-success" onClick={this.next}>Next</button>
                                        </form>
                                    </div>
                                </div>
                            </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default ChooseWorkflowComponent

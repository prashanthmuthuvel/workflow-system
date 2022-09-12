import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';
import TicketService from '../services/TicketService';
import { getToken } from '../utils/common';


class ViewFormComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            formid: this.props.match.params.formid,
            elements: {},
            dummy: '',
            isLoaded: false
        }
        this.Input = this.Input.bind(this);
        this.Element = this.Element.bind(this);
        this.back = this.back.bind(this);
    }

    componentDidMount(){
        const token = getToken();
        
        TicketService.getForm(token, this.state.id, this.state.formid).then( res => {
            let responseData = res.data;
            this.state.elements.name = responseData.form.formName;
            this.state.elements.fields = JSON.parse(responseData.filledFormMetadata);
            console.log(this.state.elements);
            console.log(this.state.elements.fields);
            this.setState({
                isLoaded: true
              });
        })
        
    }

    back(){
        this.props.history.push(`/viewTicket/${this.state.id}`);
    }

    Input = ({ fieldId, fieldLabel, fieldPlaceholder, fieldValue }) => {
        return (
            <div className="mb-3">
                <label htmlFor="exampleInputEmail1" className="form-label">{fieldLabel}</label>
                <input readOnly type="text" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                    value={fieldValue}
                />
            </div>
        )
    }

    Element = ({ field: { fieldType, fieldId, fieldLabel, fieldPlaceholder, fieldValue, fieldOptions } }) => {

        return (<this.Input
                    fieldId={fieldId}
                    fieldLabel={fieldLabel}
                    fieldPlaceholder={fieldPlaceholder}
                    fieldValue={fieldValue}
    
                />)
    }

  render() {
      const isLoaded = this.state.isLoaded;
      if(!isLoaded) {
        return <div>Loading...</div>;
      } else {
        return (
            <div>
            <Navbar />
            <div className="App container">
              <h3>{ this.state.elements.name !== undefined ? <p>{this.state.elements.name}</p> : null }</h3>
              <form>
                {this.state.elements.fields ? this.state.elements.fields.map((field, i) => <this.Element key={i} field={field} />) : null}
                <div className = "row">
                    <button className="btn btn-primary" onClick={this.back}>Back</button>
                </div>
              </form>
      
            </div>
            </div>
          )
      }
      
  }



}

export default ViewFormComponent

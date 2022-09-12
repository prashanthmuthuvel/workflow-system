import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';
import TicketService from '../services/TicketService';
import UploadFilesService from '../services/UploadFilesService';
import { getToken } from '../utils/common';
import {Circles} from 'react-loader-spinner';


class HybridPathComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            // step 2
            loading: false,
            ticketId: this.props.match.params.id,
            selectedFiles: undefined,
            currentFile: undefined,
            progress: 0,
            message: "",
            workflowForms: '',
            fileInfos: []
        }
        this.selectFile = this.selectFile.bind(this);
        this.upload = this.upload.bind(this);
        this.deleteFile = this.deleteFile.bind(this);
        this.extractData = this.extractData.bind(this);
    }

    componentDidMount(){
        const token = getToken();
        UploadFilesService.getFiles(token, this.state.ticketId).then((response) => {
            this.setState({
              fileInfos: response.data,
            });
          });
        UploadFilesService.getWorkflowForms(token, this.state.ticketId).then((response) => {
          this.setState({
            workflowForms: response.data,
          });
        });
    }

    selectFile(event) {
        this.setState({
          selectedFiles: event.target.files,
        });
    }

    deleteFile(id){
        const token = getToken();
        UploadFilesService.deleteFile(token, id).then( res => {
            UploadFilesService.getFiles(token, this.state.ticketId).then((response) => {
            this.setState({
              fileInfos: response.data,
            });
          });
        });
    }

    extractData = (e) => {
      this.setState({
        loading: true
      });
      const token = getToken();
      UploadFilesService.extractData(token, this.state.ticketId).then((response) => {
        this.setState({
          loading: false
          
        });
        this.props.history.push(`/dashboard`);
      });
      
    }

    handleChange = (fileId) => (event) => {
      const token = getToken();
      UploadFilesService.linkFileWithForm(token, this.state.ticketId, event.target.value, fileId).then((response) => {
        console.log('did the change')
      });
    };

    upload() {
        const token = getToken();
        let currentFile = this.state.selectedFiles[0];
    
        this.setState({
          progress: 0,
          currentFile: currentFile,
        });
    
        UploadFilesService.upload(token, this.state.ticketId, currentFile, (event) => {
          this.setState({
            progress: Math.round((100 * event.loaded) / event.total),
          });
        })
          .then((response) => {
            this.setState({
              message: response.data.message,
            });
            return UploadFilesService.getFiles(token, this.state.ticketId);
          })
          .then((files) => {
            this.setState({
              fileInfos: files.data,
            });
          })
          .catch(() => {
            this.setState({
              progress: 0,
              message: "Could not upload the file!",
              currentFile: undefined,
            });
          });
    
        this.setState({
          selectedFiles: undefined,
        });
      }


    render() {
        const {
            selectedFiles,
            currentFile,
            progress,
            message,
            fileInfos,
          } = this.state;
      
          if(this.state.loading){
            return ( 
              <div style={{display: 'flex',  justifyContent:'center', alignItems:'center', height: '100vh'}}>
                <Circles color="#00BFFF" height={80} width={80}/>
              </div>
             );
          }
          else {
            return ( 

              <div>
               <Navbar />
               <div>
                <br></br>
                <div className = "container">
                    <div>
                    {currentFile && (
                        <div className="progress">
                        <div
                            className="progress-bar progress-bar-info progress-bar-striped"
                            role="progressbar"
                            aria-valuenow={progress}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progress + "%" }}
                        >
                            {progress}%
                        </div>
                        </div>
                    )}
            
                    <label className="btn btn-default">
                        <input type="file" onChange={this.selectFile} />
                    </label>
            
                    <button
                        className="btn btn-success"
                        disabled={!selectedFiles}
                        onClick={this.upload}
                    >
                        Upload
                    </button>
            
                    <div className="alert alert-light" role="alert">
                        {message}
                    </div>
            
                    <div className = "row">
                        <table className = "table table-striped table-bordered">
            
                            <thead>
                                <tr>
                                    <th> File Name</th>
                                    <th> Form Option</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                    {fileInfos &&
                                fileInfos.map((file, index) => (
                                    <tr key={index}>
                                    <td><a href={file.url}>{file.name}</a></td>
                                    <td><select className="form-select" aria-label="Default select example"
                                                    onChange={this.handleChange(file.id)}>
                                                    <option >Open this select menu</option>
                                                    {this.state.workflowForms.length > 0 && this.state.workflowForms.map((option, i) =>
                                                        <option value={option.optionLabel} key={i}>{option.optionLabel}</option>
                                    
                                                    )}
                                                </select></td>
                                    <td><button onClick={ () => this.deleteFile(file.id)} className="btn btn-danger">Delete </button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
            
                    </div>
                    <div className = "form-group">
                      <button className="btn btn-success" onClick={this.extractData}>Extract Data</button>
                    </div>
                    </div>
                    </div>
                </div>
            </div>

             );
          }
    }


}

export default HybridPathComponent

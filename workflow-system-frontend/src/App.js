import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import LoginComponent from './components/LoginComponent';
import FooterComponent from './components/FooterComponent';
import DashboardComponent from './components/DashboardComponent';
import LogoutComponent from './components/LogoutComponent';
import SupportComponent from './components/SupportComponent';
import NewTicketComponent from './components/NewTicketComponent';
import CreateWorkflowComponent from './components/CreateWorkflowComponent';
import ChooseWorkflowComponent from './components/chooseWorkflowComponent';
import HybridPathComponent from './components/HybridPathComponent';
import TicketCreationComponent from './components/TicketCreationComponent';
import ListTicketComponent from './components/ListTicketComponent';
import ViewTicketComponent from './components/ViewTicketComponent';
import EditFormComponent from './components/EditFormComponent';
import ViewFormComponent from './components/ViewFormComponent';



function App() {
  
  return (
    <div>
        <Router>
                <div>
                    <Switch> 
                          <Route path="/" exact component = {LoginComponent}></Route>
                          {/*<Route path = "/" exact component = {ListStudentComponent}></Route> */}
                          <Route path = "/chooseWorkflow" exact component = {ChooseWorkflowComponent}></Route>
                          <Route path = "/createWorkflow" exact component= {CreateWorkflowComponent}></Route>
                          <Route path="/hybridPath/:id" exact component={HybridPathComponent}></Route>
                          <Route path = "/newTicket" exact component= {NewTicketComponent}></Route>
                          <Route path = "/createTicket" exact component = {TicketCreationComponent}></Route>
                          <Route path = "/listTicket" exact component = {ListTicketComponent}></Route>
                          <Route path = "/viewTicket/:id" exact component = {ViewTicketComponent}></Route>
                          <Route path = "/ticket/:id/editForm/:formid" exact component={EditFormComponent}></Route>
                          <Route path = "/ticket/:id/viewForm/:formid" exact component={ViewFormComponent}></Route>
                          <Route path = "/dashboard" component = {DashboardComponent}></Route>
                          <Route path = "/logout" component = {LogoutComponent}></Route>
                          <Route path = "/support" component = {SupportComponent}></Route>
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;

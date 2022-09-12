import React, { useState } from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import { Link } from 'react-router-dom';
import { AdminSidebarData } from './AdminSidebarData';
import { StudentSidebarData } from './StudentSidebarData';
import './Navbar.css';
import { IconContext } from 'react-icons';
import { getToken, getIsAdmin, removeUserSession, setUserSession } from '../../utils/common';

function Navbar() {
  const [sidebar, setSidebar] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);
  const token = getToken();
  const isAdmin = getIsAdmin();

  if(isAdmin == null) {
    return (
      <div>
          <header>
              <nav className="navbar navbar-expand-md navbar-dark bg-dark">
              <div><a className="navbar-brand">Workflow Management App</a></div>
              </nav>
          </header>
      </div>
  )
  }
  if(isAdmin == 'true') {
    return (
      <>
        <IconContext.Provider value={{ color: '#fff' }}>
          <div className="navbar navbar-expand-md navbar-dark bg-dark">
            <Link to='#' className='menu-bars'>
              <FaIcons.FaBars onClick={showSidebar} />
            </Link>
            <div><a className="navbar-brand">Workflow Management App</a></div>
          </div>
          <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
            <ul className='nav-menu-items' onClick={showSidebar}>
              <li className='navbar-toggle'>
                <Link to='#' className='menu-bars'>
                  <AiIcons.AiOutlineClose />
                </Link>
              </li>
              {AdminSidebarData.map((item, index) => {
                return (
                  <li key={index} className={item.cName}>
                    <Link to={item.path}>
                      {item.icon}
                      <span>{item.title}</span>
                    </Link>
                  </li>
                );
              })}
            </ul>
          </nav>
        </IconContext.Provider>
      </>
    );
  } else {
    return (
      <>
        <IconContext.Provider value={{ color: '#fff' }}>
          <div className="navbar navbar-expand-md navbar-dark bg-dark">
            <Link to='#' className='menu-bars'>
              <FaIcons.FaBars onClick={showSidebar} />
            </Link>
            <div><a className="navbar-brand">Workflow Management App</a></div>
          </div>
          <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
            <ul className='nav-menu-items' onClick={showSidebar}>
              <li className='navbar-toggle'>
                <Link to='#' className='menu-bars'>
                  <AiIcons.AiOutlineClose />
                </Link>
              </li>
              {StudentSidebarData.map((item, index) => {
                return (
                  <li key={index} className={item.cName}>
                    <Link to={item.path}>
                      {item.icon}
                      <span>{item.title}</span>
                    </Link>
                  </li>
                );
              })}
            </ul>
          </nav>
        </IconContext.Provider>
      </>
    );
  }
}

export default Navbar;

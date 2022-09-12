import React from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';

export const AdminSidebarData = [
  {
    title: 'Home',
    path: '/',
    icon: <AiIcons.AiFillHome />,
    cName: 'nav-text'
  },
  {
    title: 'New Ticket',
    path: '/chooseWorkflow',
    icon: <FaIcons.FaTicketAlt />,
    cName: 'nav-text'
  },
  {
    title: 'List Ticket',
    path: '/listTicket',
    icon: <FaIcons.FaClipboardList />,
    cName: 'nav-text'
  },
  {
    title: 'Create Workflow',
    path: '/createWorkflow',
    icon: <FaIcons.FaTicketAlt />,
    cName: 'nav-text'
  },
  {
    title: 'Support',
    path: '/support',
    icon: <IoIcons.IoMdHelpCircle />,
    cName: 'nav-text'
  },
  {
    title: 'Logout',
    path: '/logout',
    icon: <IoIcons.IoIosLogOut />,
    cName: 'nav-text'
  }
];

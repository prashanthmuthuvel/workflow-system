// return the user data from the session storage
export const getUser = () => {
    const userStr = localStorage.getItem('user');
    if (userStr) return userStr;
    else return null;
  }
  
  // return the token from the session storage
  export const getToken = () => {
    return localStorage.getItem('token') || null;
  }

  export const getIsAdmin = () => {
    const isAdmin = localStorage.getItem('isAdmin');
    if (isAdmin) return isAdmin;
    else return null;
  }

  export const setWorkflowName = (workflowName) => {
    localStorage.setItem('workflowName', workflowName);
  }

  export const getWorkflowName = () => {
    return localStorage.getItem('workflowName') || null;
  }

  export const removeWorkflowName = () => {
    localStorage.removeItem('workflowName');
  }
  
  // remove the token and user from the session storage
  export const removeUserSession = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('isAdmin')
  }
  
  // set the token and user from the session storage
  export const setUserSession = (token, user, isAdmin) => {
    localStorage.setItem('token', token);
    localStorage.setItem('user', user);
    localStorage.setItem('isAdmin', isAdmin);
  }
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import './ForumsNavBar.css'
import {useAuth0} from "@auth0/auth0-react";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {unAuthenticate} from "../../redux/auth/authSlice";

export const ForumsNavBar = ({ userName }) => {
    const { logout } = useAuth0();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    function logoutClicked() {
        dispatch(unAuthenticate());
        logout({ returnTo: window.location.origin });
    }


    function navigateToProfile() {
        navigate(`/profile`);
    }

    function navigateToForums() {
        navigate(`/`);
    }

    function navigateToChat() {
        navigate(`/chat`);
    }

    return (<Navbar expand="lg" variant="dark" bg="dark">
        <Navbar.Brand>The Forums</Navbar.Brand>
        <Navbar.Toggle/>
        <Navbar.Collapse className="justify-content-end">
            <Nav className='me-auto'>
                <Nav.Link onClick={navigateToForums}>Forums</Nav.Link>
                <Nav.Link onClick={navigateToChat}>Chat</Nav.Link>
            </Nav>
            <NavDropdown title={userName} id="nav-dropdown">
                <NavDropdown.Item as="button" onClick={navigateToProfile}>Profile</NavDropdown.Item>
                <NavDropdown.Item as="button" onClick={logoutClicked}>Logout</NavDropdown.Item>
            </NavDropdown>
        </Navbar.Collapse>
    </Navbar>);
}
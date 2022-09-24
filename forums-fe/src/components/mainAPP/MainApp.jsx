import {Route, Routes} from "react-router-dom";
import Container from "react-bootstrap/Container";
import {ForumsNavBar} from "../navbar/ForumsNavBar";
import {ForumsListPage} from "../forumsListPage/ForumsListPage";
import {useAuth0, withAuthenticationRequired} from "@auth0/auth0-react";
import {useEffect} from "react";
import {authenticate} from "../../redux/auth/authSlice";
import parseJwt from "../../utils/parseJwt";
import {useDispatch, useSelector} from "react-redux";
import {ForumPage} from "../forumPage/ForumPage";
import {ProfilePage} from "../profilePage/ProfilePage";
import {PostPage} from "../postPage/PostPage";
import {Spinner} from "react-bootstrap";
import {fetchUsers} from "../../redux/users/usersSlice";
import {ChatsListPage} from "../chatList/ChatListPage";
import {ChatMessagesListPage} from "../chatMessages/ChatMessagesListPage";

const ProtectedRoute = ({ component, ...args }) => {
    const Component = withAuthenticationRequired(component, {...args, onRedirecting: () => <Spinner animation="border" />});
    return <Component/>;
};

export const MainApp = () => {
    const { getAccessTokenSilently, user, isAuthenticated } = useAuth0();
    const dispatch = useDispatch();
    const authenticatedUser = useSelector(state => state.auth.user);
    const isAuthStateIsAuthenticated = useSelector(state => state.auth.isAuthenticated);

    useEffect(() => {
        const initAuthentication = async () => {
            const token = await getAccessTokenSilently();
            if(token && user && isAuthenticated) {
                dispatch(authenticate({
                    isAuthenticated: isAuthenticated,
                    user: user,
                    token: token,
                    parsedToken: parseJwt(token)
                }));
            }
        }
        initAuthentication();

    }, [getAccessTokenSilently, user, isAuthenticated]);

    useEffect(() => {
        dispatch(fetchUsers());
    }, [isAuthStateIsAuthenticated]);

    return <Container>
        <ForumsNavBar userName={authenticatedUser && authenticatedUser.name}/>
        <Routes>
            <Route path="/" element={ <ProtectedRoute component={ForumsListPage}/> }/>
            <Route path="/profile" element={ <ProtectedRoute component={ProfilePage}/> }/>
            <Route path="/chat" element={ <ProtectedRoute component={ChatsListPage}/> }/>
            <Route path="/chat/:otherId" element={ <ProtectedRoute component={ChatMessagesListPage}/> }/>
            <Route path="/forum/:forumId" element={ <ProtectedRoute component={ForumPage}/>}/>
            <Route path="/forum/:forumId/post/:postId" element={ <ProtectedRoute component={PostPage}/>}/>
        </Routes>
    </Container>
}

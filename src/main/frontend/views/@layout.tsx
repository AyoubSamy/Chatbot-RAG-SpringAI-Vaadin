import {NavLink,Outlet} from "react-router-dom";

export default function Layout(){
    return(
        <div className="p-m">
            <nav>
                <NavLink className="btn btn-outline-info m-1" to="/">Home</NavLink>
                {/*utiliser Navlink pour gerer les routes et naviguer entre chat et home*/}
                <NavLink className="btn btn-outline-info m-1" to="/chat">Chat</NavLink>
            </nav>
            <main>
                <Outlet></Outlet>
            </main>
        </div>
    );
}
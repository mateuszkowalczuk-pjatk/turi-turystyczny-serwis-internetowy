import {ReactNode} from "react";
import styles from "./AuthLayout.module.css"
import AuthHeader from "../../components/AuthHeader";
import AuthFooter from "../../components/AuthFooter";

interface Props {
    content: ReactNode;
}

const AuthLayout = ({ content }: Props) => {
    return (
        <div className={styles.auth}>
            <AuthHeader />
            { content }
            <AuthFooter  />
        </div>
    )
}

export default AuthLayout;
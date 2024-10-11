import {ReactNode} from "react";
import AuthHeader from "../../components/AuthHeader";
import AuthFooter from "../../components/AuthFooter";
import styles from "./AuthLayout.module.css"

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
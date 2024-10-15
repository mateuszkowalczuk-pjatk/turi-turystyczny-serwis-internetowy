import { ReactNode } from "react";
import styles from './SignUpPanel.module.css'

interface Props {
    header: ReactNode;
    login: ReactNode;
    email: ReactNode;
    password: ReactNode;
    rePassword: ReactNode;
    button: ReactNode;
    top: ReactNode;
    down: ReactNode;
}

const SignUpPanel = ({ header, login, email, password, rePassword, button, top, down}: Props) => {
    return (
        <div className={styles.panel}>
            { header }
            { login }
            { email }
            { password }
            { rePassword }
            { button }
            { top }
            { down }
        </div>
    )
}

export default SignUpPanel;
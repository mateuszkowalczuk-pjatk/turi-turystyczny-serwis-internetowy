import { ReactNode } from 'react'
import styles from './AuthPanel.module.css'

interface Props {
    header: ReactNode;
    option: ReactNode;
    input: ReactNode;
    button: ReactNode;
    top: ReactNode;
    down: ReactNode;
}

const AuthPanel = ({ header, option, input, button, top, down }: Props) => {
    return (
        <div className={styles.panel}>
            { header }
            { option }
            { input }
            { button }
            { top }
            { down }
        </div>
    )
}

export default AuthPanel
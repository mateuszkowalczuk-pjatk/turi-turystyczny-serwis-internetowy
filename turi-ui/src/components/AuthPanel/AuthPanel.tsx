import { ReactNode } from 'react'
import styles from './AuthPanel.module.css'

interface Props {
    header: ReactNode;
    option: ReactNode;
    input: ReactNode;
    button: ReactNode;
    send?: ReactNode;
    back: ReactNode;
}

const AuthPanel = ({ header, option, input, button, send, back }: Props) => {
    return (
        <div className={styles.panel}>
            { header }
            { option }
            { input }
            { button }
            { send }
            { back }
        </div>
    )
}

export default AuthPanel;
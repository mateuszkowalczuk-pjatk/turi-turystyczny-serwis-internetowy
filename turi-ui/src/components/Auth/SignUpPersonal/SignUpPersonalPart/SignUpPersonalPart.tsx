import { ReactNode } from 'react'
import styles from './SignUpPersonalPart.module.css'

interface Props {
    firstPanel: ReactNode;
    secondPanel: ReactNode;
    option: ReactNode;
}

const SignUpPersonalPart = ({ firstPanel, secondPanel, option }: Props) => {
    return (
        <div className={styles.part}>
            { firstPanel }
            { secondPanel }
            { option }
        </div>
    )
}

export default SignUpPersonalPart;
import { ReactNode } from 'react'
import styles from './PersonalPart.module.css'

interface Props {
    firstPanel: ReactNode;
    secondPanel: ReactNode;
    option?: ReactNode;
}

const PersonalPart = ({ firstPanel, secondPanel, option }: Props) => {
    return (
        <div className={styles.part}>
            { firstPanel }
            { secondPanel }
            { option }
        </div>
    )
}

export default PersonalPart;
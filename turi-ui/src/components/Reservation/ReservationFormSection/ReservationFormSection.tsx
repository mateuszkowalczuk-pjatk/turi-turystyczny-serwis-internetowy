import { ReactNode } from 'react'
import styles from './ReservationFormSection.module.css'

interface Props {
    leftPanel: ReactNode
    rightPanel: ReactNode
}

const ReservationFormSection = ({ leftPanel, rightPanel }: Props) => {
    return (
        <div className={styles.section}>
            {leftPanel}
            {rightPanel}
        </div>
    )
}

export default ReservationFormSection

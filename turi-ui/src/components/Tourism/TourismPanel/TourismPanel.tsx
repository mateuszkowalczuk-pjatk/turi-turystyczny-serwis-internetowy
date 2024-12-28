import { ReactNode } from 'react'
import styles from './TourismPanel.module.css'

interface Props {
    header: ReactNode
    content: ReactNode
    size: 'current' | 'reservations' | 'place' | 'offer'
}

const TourismPanel = ({ header, content, size }: Props) => {
    return (
        <div className={`${styles.box} ${styles[size]}`}>
            <div className={`${styles.panel} ${styles[size]}`}>
                {header}
                {content}
            </div>
        </div>
    )
}

export default TourismPanel

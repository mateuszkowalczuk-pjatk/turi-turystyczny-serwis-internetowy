import { ReactNode } from 'react'
import styles from './PremiumPanel.module.css'

const PremiumPanel = ({ content }: { content: ReactNode }) => {
    return <div className={styles.panel}>{content}</div>
}

export default PremiumPanel

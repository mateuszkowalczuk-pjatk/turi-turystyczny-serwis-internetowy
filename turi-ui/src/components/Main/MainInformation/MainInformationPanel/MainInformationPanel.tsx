import { ReactNode } from 'react'
import styles from './MainInformationPanel.module.css'

const MainInformationPanel = ({ content }: { content: ReactNode }) => {
    return <div className={styles.panel}>{content}</div>
}

export default MainInformationPanel

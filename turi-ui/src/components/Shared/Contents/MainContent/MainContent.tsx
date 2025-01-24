import { ReactNode } from 'react'
import styles from './MainContent.module.css'

const MainContent = ({ content }: { content?: ReactNode }) => {
    return <div className={styles.content}>{content}</div>
}

export default MainContent

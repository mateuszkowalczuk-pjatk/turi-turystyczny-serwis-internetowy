import { ReactNode } from 'react'
import styles from './DefaultContent.module.css'

const DefaultContent = ({ content }: { content: ReactNode }) => {
    return <div className={styles.content}>{content}</div>
}

export default DefaultContent

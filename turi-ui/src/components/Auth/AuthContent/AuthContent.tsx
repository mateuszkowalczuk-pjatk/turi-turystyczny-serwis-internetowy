import { ReactNode } from 'react'
import styles from './AuthContent.module.css'

const AuthContent = ({ content }: { content: ReactNode }) => {
    return <div className={styles.content}>{content}</div>
}

export default AuthContent

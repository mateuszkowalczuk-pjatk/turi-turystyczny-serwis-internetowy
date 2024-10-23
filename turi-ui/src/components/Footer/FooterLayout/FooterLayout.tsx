import { ReactNode } from 'react'
import styles from './FooterLayout.module.css'

const FooterLayout = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.layout}>
            { content }
        </div>
    )
}

export default FooterLayout
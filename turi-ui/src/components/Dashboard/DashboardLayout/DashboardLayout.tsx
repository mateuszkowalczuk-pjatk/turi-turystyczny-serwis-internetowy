import { ReactNode } from 'react'
import styles from './DashboardLayout.module.css'

const DashboardLayout = ({ content }: { content: ReactNode }) => {
    return <div className={styles.layout}>{content}</div>
}

export default DashboardLayout

import { ReactNode } from 'react'
import Dashboard from '../../components/Dashboard'
import Proposition from '../../components/Proposition'
import styles from '../Layout.module.css'

const HomeLayout = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.layout}>
            <Dashboard />
            { content }
            <Proposition />
        </div>
    )
}

export default HomeLayout;
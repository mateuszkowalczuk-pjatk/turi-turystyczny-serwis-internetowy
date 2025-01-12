import { ReactNode } from 'react'
import { Outlet } from 'react-router-dom'
import AuthHeader from '../../components/Shared/Header/AuthHeader'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import styles from '../Layout.module.css'

const DefaultLayout = ({ content }: { content?: ReactNode }) => {
    return (
        <div className={styles.layout}>
            <AuthHeader />
            {content ? content : <Outlet />}
            <DefaultFooter />
        </div>
    )
}

export default DefaultLayout

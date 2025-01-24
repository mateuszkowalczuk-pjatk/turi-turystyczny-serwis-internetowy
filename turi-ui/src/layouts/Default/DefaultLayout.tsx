import { ReactNode } from 'react'
import { Outlet } from 'react-router-dom'
import Loader from '../../components/Shared/Loading/Loader'
import Layout from '../../components/Shared/Layout'
import AuthHeader from '../../components/Shared/Header/AuthHeader'
import DefaultContent from '../../components/Shared/Contents/DefaultContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'

const DefaultLayout = ({ content }: { content?: ReactNode }) => {
    return (
        <Loader>
            <Layout
                header={<AuthHeader />}
                content={<DefaultContent content={content ? content : <Outlet />} />}
                footer={<DefaultFooter />}
            />
        </Loader>
    )
}

export default DefaultLayout

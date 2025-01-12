import { ReactNode } from 'react'
import { Outlet } from 'react-router-dom'
import Layout from '../../components/Shared/Layout'
import AuthHeader from '../../components/Shared/Header/AuthHeader'
import DefaultContent from '../../components/Shared/Contents/DefaultContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'

const DefaultLayout = ({ content }: { content?: ReactNode }) => {
    return (
        <Layout
            header={<AuthHeader />}
            content={<DefaultContent content={content ? content : <Outlet />} />}
            footer={<DefaultFooter />}
        />
    )
}

export default DefaultLayout

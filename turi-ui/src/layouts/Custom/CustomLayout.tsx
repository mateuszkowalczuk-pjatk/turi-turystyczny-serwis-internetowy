import { useStates } from '../../hooks/useStates.ts'
import { Outlet } from 'react-router-dom'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import CustomContent from '../../components/Shared/Contents/CustomContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import UserHeader from '../../components/Shared/Header/UserHeader'
import Layout from '../../components/Shared/Layout'

const CustomLayout = ({ profile }: { profile?: boolean }) => {
    const { isPremium } = useStates()

    return (
        <Layout
            header={isPremium ? <PremiumHeader /> : <UserHeader />}
            content={
                <CustomContent
                    profile={profile}
                    content={<Outlet />}
                />
            }
            footer={<DefaultFooter />}
        />
    )
}

export default CustomLayout

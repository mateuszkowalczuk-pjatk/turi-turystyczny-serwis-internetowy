import { Outlet } from 'react-router-dom'
import { usePremium } from '../../store/slices/premium.ts'
import Layout from '../../components/Shared/Layout'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import CustomContent from '../../components/Shared/Contents/CustomContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'

const CustomLayout = ({ profile }: { profile?: boolean }) => {
    const isPremium = usePremium()

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

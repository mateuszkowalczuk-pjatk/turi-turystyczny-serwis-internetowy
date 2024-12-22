import { RootState } from '../../store/store.ts'
import { Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import PremiumContent from '../../components/Premium/PremiumContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import styles from '../Layout.module.css'

const PremiumLayout = () => {
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isPremium ? <PremiumHeader /> : <UserHeader />}
            <PremiumContent content={<Outlet />} />
            <DefaultFooter />
        </div>
    )
}

export default PremiumLayout

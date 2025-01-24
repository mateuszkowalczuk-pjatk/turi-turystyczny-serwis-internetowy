import { ReactNode } from 'react'
import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import PageTitle from '../../PageTitle'
import ProfilePanel from '../../../Profile/ProfilePanel'
import PremiumPanel from '../../../Premium/PremiumPanel'
import styles from './CustomContent.module.css'

interface Props {
    profile?: boolean
    content: ReactNode
}

const CustomContent = ({ profile, content }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.content}>
            <PageTitle text={t(profile ? 'profile.title' : 'premium.title')} />
            {profile ? <ProfilePanel content={content} /> : <PremiumPanel content={content} />}
        </div>
    )
}

export default CustomContent

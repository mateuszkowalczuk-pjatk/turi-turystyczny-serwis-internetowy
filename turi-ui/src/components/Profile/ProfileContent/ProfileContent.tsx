import { ReactNode } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageTitle from '../../Shared/PageTitle'
import ProfilePanel from '../ProfilePanel'
import styles from './ProfileContent.module.css'

const ProfileContent = ({ content }: { content: ReactNode }) => {
    const { t } = useHooks()

    return (
        <div className={styles.content}>
            <PageTitle text={t('profile.title')} />
            <ProfilePanel content={content} />
        </div>
    )
}

export default ProfileContent

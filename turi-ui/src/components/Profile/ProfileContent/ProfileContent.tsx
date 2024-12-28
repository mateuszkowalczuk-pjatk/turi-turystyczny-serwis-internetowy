import { ReactNode } from 'react'
import { useTranslation } from 'react-i18next'
import PageTitle from '../../Shared/PageTitle'
import ProfilePanel from '../ProfilePanel'
import styles from './ProfileContent.module.css'

const ProfileContent = ({ content }: { content: ReactNode }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.content}>
            <PageTitle text={t('profile.title')} />
            <ProfilePanel content={content} />
        </div>
    )
}

export default ProfileContent

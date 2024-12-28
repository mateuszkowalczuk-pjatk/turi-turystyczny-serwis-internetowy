import { ReactNode } from 'react'
import { useTranslation } from 'react-i18next'
import PageTitle from '../../Shared/PageTitle'
import PremiumPanel from '../PremiumPanel'
import styles from './PremiumContent.module.css'

const PremiumContent = ({ content }: { content: ReactNode }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.content}>
            <PageTitle text={t('premium.title')} />
            <PremiumPanel content={content} />
        </div>
    )
}

export default PremiumContent

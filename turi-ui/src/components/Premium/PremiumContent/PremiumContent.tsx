import { ReactNode } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageTitle from '../../Shared/PageTitle'
import PremiumPanel from '../PremiumPanel'
import styles from './PremiumContent.module.css'

const PremiumContent = ({ content }: { content: ReactNode }) => {
    const { t } = useHooks()

    return (
        <div className={styles.content}>
            <PageTitle text={t('premium.title')} />
            <PremiumPanel content={content} />
        </div>
    )
}

export default PremiumContent

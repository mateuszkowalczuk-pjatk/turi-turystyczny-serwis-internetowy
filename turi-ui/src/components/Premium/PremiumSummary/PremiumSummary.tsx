import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { Trans } from 'react-i18next'
import { Link } from 'react-router-dom'
import styles from './PremiumSummary.module.css'

const PremiumSummary = () => {
    const { t } = useHooks()

    return (
        <div className={styles.summary}>
            <p>
                <Trans
                    i18nKey="premium.summary-first-text"
                    components={{
                        1: (
                            <Link
                                to="/tourism"
                                className={styles.link}
                            />
                        )
                    }}
                />
            </p>
            <p>
                <Trans
                    i18nKey="premium.summary-second-text"
                    components={{
                        strong: <strong />,
                        1: (
                            <Link
                                to="/profile/premium"
                                className={styles.link}
                            />
                        )
                    }}
                />
            </p>
            <p>{t('premium.summary-third-text')}</p>
        </div>
    )
}

export default PremiumSummary

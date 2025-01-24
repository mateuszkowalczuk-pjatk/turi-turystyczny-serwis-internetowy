import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import FooterTitle from '../FooterTitle'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterModule from '../FooterModule'
import FooterContent from '../FooterContent'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import FooterCopyright from '../FooterCopyright'
import { Link } from 'react-router-dom'
import styles from '../Footer.module.css'

const PremiumFooter = () => {
    const { t } = useHooks()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterTitle />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.your-account')} />}
                            firstOption={
                                <Link
                                    to="/profile"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.profile')} />
                                </Link>
                            }
                            secondOption={
                                <Link
                                    to="/reservations"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.reservations')} />
                                </Link>
                            }
                            thirdOption={
                                <Link
                                    to="/realized"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.realized')} />
                                </Link>
                            }
                            fourthOption={
                                <Link
                                    to="/tourism"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.tourism')} />
                                </Link>
                            }
                        />
                    }
                />
                <FooterCopyright />
            </div>
        </div>
    )
}

export default PremiumFooter

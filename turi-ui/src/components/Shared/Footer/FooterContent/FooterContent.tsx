import { ReactNode } from 'react'
import { useAuthenticated } from '../../../../store/slices/auth.ts'
import { useTranslation } from 'react-i18next'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import { Link } from 'react-router-dom'
import styles from './FooterContent.module.css'

const FooterContent = ({ content }: { content: ReactNode }) => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()

    return (
        <div className={styles.content}>
            {content}
            <FooterModule
                title={<TextExtraLight text={t('footer.search-offers')} />}
                firstOption={
                    isAuthenticated ? (
                        <Link
                            to="/favourites"
                            className={styles.link}
                        >
                            <TextRegular text={t('header.favourites')} />
                        </Link>
                    ) : null
                }
                secondOption={
                    <Link
                        to="/search?mode=STAY"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.stays')} />
                    </Link>
                }
                thirdOption={
                    <Link
                        to="/search?mode=ATTRACTION"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.attractions')} />
                    </Link>
                }
            />
            <FooterModule
                title={<TextExtraLight text={t('footer.read-more')} />}
                firstOption={
                    <Link
                        to="/info"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.about-turi')} />
                    </Link>
                }
                secondOption={
                    <Link
                        to="/info/terms-of-use"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.terms-of-use')} />
                    </Link>
                }
                thirdOption={
                    <Link
                        to="/info/privacy-policy"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.privacy-policy')} />
                    </Link>
                }
            />
            <FooterModule
                title={<TextExtraLight text={t('footer.contact')} />}
                firstOption={<TextRegular text={t('footer.email')} />}
                secondOption={<TextRegular text={t('footer.phone-number')} />}
            />
        </div>
    )
}

export default FooterContent
